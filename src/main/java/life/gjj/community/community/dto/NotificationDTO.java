package life.gjj.community.community.dto;

import life.gjj.community.community.model.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    private String notifierName;
    private  String outterTitle;
    private String type;
}
