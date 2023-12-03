package node_value.projects.cerasync_back.util.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data @Builder
public class UserDTO {
    @NonNull private String email, password;
}
