package booker.BookingApp.controller.users;

import booker.BookingApp.dto.users.*;
import booker.BookingApp.enums.Role;
import booker.BookingApp.model.users.Guest;
import booker.BookingApp.model.users.Owner;
import booker.BookingApp.model.users.User;
import booker.BookingApp.service.implementation.UserService;
import booker.BookingApp.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenUtils tokenUtils;


    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping(value = "/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.findAll();

        List<UserDTO> usersDTO = new ArrayList<>();
        for (User u : users) {
            usersDTO.add(new UserDTO(u));
        }

        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        User user = userService.findOne(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
    }



    @PostMapping(value = "/signup", consumes = "application/json")
    public ResponseEntity<CreateUserDTO> saveUser(@RequestBody CreateUserDTO createUserDTO) throws InterruptedException {

        User user = null;
        if(createUserDTO.getRole() == Role.GUEST) {
            user = new Guest();
        }
        else if(createUserDTO.getRole() == Role.OWNER) {
            user = new Owner();
        }

        user.setName(createUserDTO.getName());
        user.setSurname(createUserDTO.getSurname());
        user.setEmail(createUserDTO.getEmail());
        user.setPassword(createUserDTO.getPassword());
        user.setAddress(createUserDTO.getAddress());
        user.setPhone(createUserDTO.getPhone());
        user.setRole(createUserDTO.getRole());
        user = userService.save(user);
        return new ResponseEntity<>(new CreateUserDTO(user), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<UpdateUserDTO> updateUser(@RequestBody UpdateUserDTO updateUserDTO) throws InterruptedException {
        User user = userService.findOne(updateUserDTO.getId());

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        user.setName(updateUserDTO.getName());
        user.setSurname(updateUserDTO.getSurname());
        user.setEmail(updateUserDTO.getEmail());
        user.setPassword(updateUserDTO.getPassword());
        user.setAddress(updateUserDTO.getAddress());
        user.setPhone(updateUserDTO.getPhone());
        user.setProfilePicture(updateUserDTO.getProfilePicture());


        user = userService.save(user);
        return new ResponseEntity<>(new UpdateUserDTO(user), HttpStatus.OK);
    }

    @GetMapping(value = "/by-email={email}")
    public ResponseEntity<UserDTO> findByEmail(@PathVariable String email) {
        User user = userService.findByEmail(email);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
    }

    @PostMapping(value="/login", consumes = "application/json")
    public ResponseEntity<Token> findByEmailAndPassword(@RequestBody LoginUserDTO loginUserDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginUserDTO.getEmail(), loginUserDTO.getPassword()));


        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();
        if(!user.isActivated()){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        String jwt = tokenUtils.generateToken(user.getUsername());
        Token token = new Token(user.getId(), jwt);
        return ResponseEntity.ok(token);
    }

    @PutMapping(value = "/activate_profile/{activationLink}", consumes = "application/json")
    public ResponseEntity<UserDTO> activateProfile(@PathVariable String activationLink) {
        User user = userService.activateProfile(activationLink);
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }


        UserDTO dto = new UserDTO(user);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping(value = "/{userId}/upload_images")
    public ResponseEntity<String> uploadImages(@PathVariable("userId")Long userId,
                                               @RequestParam("images") Collection<MultipartFile> imageFiles) throws IOException {
        if (imageFiles.size() != 1){
            return new ResponseEntity<>("Error! You can not upload more than one image.", HttpStatus.BAD_REQUEST);
        }
        else{
            for(MultipartFile image : imageFiles) {
                userService.uploadImage(userId, image);
            }
            return new ResponseEntity<>("Image updated successfully!", HttpStatus.OK);
        }
    }





}
