package homework_3;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDto {
    private String name;
    private String password;
    private LocalDateTime createdAt;
    private List<Post> posts;
    private List<Comment> comments;
}
