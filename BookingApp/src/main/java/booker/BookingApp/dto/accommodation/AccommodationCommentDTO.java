package booker.BookingApp.dto.accommodation;

import booker.BookingApp.model.accommodation.AccommodationComment;
import booker.BookingApp.model.users.ProfilePicture;
import lombok.Data;

import java.util.Date;

public @Data class AccommodationCommentDTO {
    private Long id;
    private Long accommodationId;
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

    public AccommodationCommentDTO() {
    }

    public AccommodationCommentDTO(AccommodationComment accommodationComment) {
        this(accommodationComment.getId(), accommodationComment.getAccommodation().getId(), accommodationComment.getUser().getId(), accommodationComment.getUser().getName(), accommodationComment.getUser().getSurname(), accommodationComment.getUser().getProfilePicture(), accommodationComment.getContent(), accommodationComment.getRating(), accommodationComment.getDate(), accommodationComment.isReported(), accommodationComment.isDeleted(), accommodationComment.isApproved());
    }

    public AccommodationCommentDTO(Long id, Long accommodationId, Long guestId, String guestName, String guestSurname, ProfilePicture guestProfilePicture, String content, double rating, Date date, boolean reported, boolean deleted, boolean approved) {
        this.id = id;
        this.accommodationId = accommodationId;
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

    public static AccommodationCommentDTO createFromAccommodationComment(AccommodationComment accommodationComment) {
        return new AccommodationCommentDTO(accommodationComment.getId(),
                                            accommodationComment.getAccommodation().getId(),
                                            accommodationComment.getUser().getId(),
                                            accommodationComment.getUser().getName(),
                                            accommodationComment.getUser().getSurname(),
                                            accommodationComment.getUser().getProfilePicture(),
                                            accommodationComment.getContent(),
                                            accommodationComment.getRating(),
                                            accommodationComment.getDate(),
                                            accommodationComment.isReported(),
                                            accommodationComment.isDeleted(),
                                            accommodationComment.isApproved());
    }
}
