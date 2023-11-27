package booker.BookingApp.dto.commentsAndRatings;

import booker.BookingApp.model.commentsAndRatings.OwnerComment;
import lombok.Data;

import java.util.Date;

public @Data class OwnerCommentDTO {
    private Long id;
    private Long ownerId;
    private Long guestId;
    private String content;
    private Date date;
    private boolean reported;

    public OwnerCommentDTO() {
    }

    public OwnerCommentDTO(OwnerComment ownerComment) {
        this(ownerComment.getId(), ownerComment.getOwner().getId(), ownerComment.getGuest().getId(), ownerComment.getContent(), ownerComment.getDate(), ownerComment.isReported());
    }

    public OwnerCommentDTO(Long id, Long ownerId, Long guestId, String content, Date date, boolean reported) {
        this.id = id;
        this.ownerId = ownerId;
        this.guestId = guestId;
        this.content = content;
        this.date = date;
        this.reported = reported;
    }
}
