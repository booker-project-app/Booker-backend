package booker.BookingApp.dto.users;

import booker.BookingApp.enums.Role;
import booker.BookingApp.model.users.User;
import lombok.Data;

public @Data class UpdateUserDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String address;
    private String phone;
    private Role role;
    private boolean blocked;
    private boolean deleted;

    public UpdateUserDTO() {
    }

    public UpdateUserDTO(User user) {
        this(user.getId(), user.getName(), user.getSurname(), user.getEmail(), user.getPassword(), user.getAddress(), user.getPhone(), user.getRole(), user.isBlocked(), user.isDeleted());
    }

    public UpdateUserDTO(Long id, String name, String surname, String email, String password, String address, String phone, Role role, boolean blocked, boolean deleted) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.role = role;
        this.blocked = blocked;
        this.deleted = deleted;
    }
}