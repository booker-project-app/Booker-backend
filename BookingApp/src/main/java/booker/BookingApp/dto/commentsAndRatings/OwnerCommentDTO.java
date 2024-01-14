package booker.BookingApp.dto.commentsAndRatings;

import booker.BookingApp.model.commentsAndRatings.OwnerComment;
import booker.BookingApp.model.users.ProfilePicture;
import lombok.Data;

import java.util.Date;

public @Data class OwnerCommentDTO {
    private Long id;
    private Long ownerId;
    private Long guestId;
    private String guestName;
    private String guestSurname;
    private ProfilePicture guestProfilePicture;
    private String content;
    private double rating;
    private Date date;
    private boolean reported;
    private boolean deleted;
    private boolean approved;

    public OwnerCommentDTO() {
    }

    public OwnerCommentDTO(OwnerComment ownerComment) {
        this(ownerComment.getId(), ownerComment.getOwner().getId(), ownerComment.getGuest().getId(), ownerComment.getGuest().getName(), ownerComment.getGuest().getSurname(), ownerComment.getGuest().getProfilePicture() ,ownerComment.getContent(), ownerComment.getRating(),ownerComment.getDate(), ownerComment.isReported(), ownerComment.isDeleted(), ownerComment.isApproved());
    }

    public OwnerCommentDTO(Long id, Long ownerId, Long guestId, String guestName, String guestSurname, ProfilePicture guestProfilePicture,String content, double rating,Date date, boolean reported, boolean deleted, boolean approved) {
        this.id = id;
        this.ownerId = ownerId;
        this.guestId = guestId;
        this.guestName = guestName;
        this.guestSurname = guestSurname;
        this.guestProfilePicture = guestProfilePicture;
        this.content = content;
        this.rating = rating;
        this.date = date;
        this.reported = reported;
        this.deleted = deleted;
        this.approved = approved;
    }

    public static OwnerCommentDTO createFromOwnerComment(OwnerComment ownerComment) {
        return new OwnerCommentDTO(ownerComment.getId(),
                ownerComment.getOwner().getId(),
                ownerComment.getGuest().getId(),
                ownerComment.getGuest().getName(),
                ownerComment.getGuest().getSurname(),
                ownerComment.getGuest().getProfilePicture(),
                ownerComment.getContent(),
                ownerComment.getRating(),
                ownerComment.getDate(),
                ownerComment.isReported(),
                ownerComment.isDeleted(),
                ownerComment.isApproved());
    }
}
