package booker.BookingApp.dto.users;

import booker.BookingApp.enums.Role;
import booker.BookingApp.model.users.User;
import booker.BookingApp.validation.PasswordCustomConstraint;
import booker.BookingApp.validation.PhoneCustomConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public @Data class CreateUserDTO {
    @NotEmpty
    public String name;
    @NotEmpty
    public String surname;
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    public String email;
    @PasswordCustomConstraint(message = "Password must contain exactly 8 digits")
    public String password;
    @NotEmpty
    public String address;
    @PhoneCustomConstraint(message = "Phone must contain exactly 10 digits")
    public String phone;
    @NotNull
    public Role role;
    public String activationLink;

    public CreateUserDTO() {
    }

    public CreateUserDTO(User user) {
        this(user.getName(), user.getSurname(), user.getEmail(), user.getPassword(), user.getAddress(), user.getPhone(), user.getRole(), user.getActivationLink());
    }

    public CreateUserDTO(String name, String surname, String email, String password, String address, String phone, Role role, String activationLink) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.role = role;
        this.activationLink = activationLink;
    }

}
