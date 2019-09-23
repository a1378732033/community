package life.gjj.community.community.service;

import life.gjj.community.community.dto.NotificationDTO;
import life.gjj.community.community.dto.PaginationDTO;
import life.gjj.community.community.enums.NotificationStatusEnum;
import life.gjj.community.community.enums.NotificationTypeEnum;
import life.gjj.community.community.exception.CustomizeErrorCode;
import life.gjj.community.community.exception.CustomizeException;
import life.gjj.community.community.mapper.NotificationMapper;
import life.gjj.community.community.mapper.UserMapper;
import life.gjj.community.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service
public class NotificationService {
    @Autowired
    NotificationMapper notificationMapper;
    @Autowired
    UserMapper userMapper;
    public PaginationDTO list(Long userId, Integer page, Integer size) {
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        Integer totalPage;

        NotificationExample notificationExample = new NotificationExample ();
        notificationExample.createCriteria().andReceiverEqualTo(userId);
        Integer totalcount = (int) notificationMapper.countByExample(notificationExample);
        if(totalcount % size==0){
            totalPage=totalcount/size;
        }else {
            totalPage=totalcount/size+1;
        }
        if(page<1){
            page=1;
        }
        if(page>totalPage){
            page=totalPage;
        }
        paginationDTO.setPagintion(totalPage,page);
        Integer offset=size*(page-1);
       NotificationExample example = new  NotificationExample();
        example.createCriteria().andReceiverEqualTo(userId);
        example.setOrderByClause("gmt_create desc");
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        if (notifications.size()==0){
            return paginationDTO;
        }
        List<NotificationDTO> notificationDTOS=new ArrayList();
        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        paginationDTO.setData(notificationDTOS);
        return paginationDTO;
    }

    public Long underadCount(Long userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId)
        .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
       return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification.getReceiver()==null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!notification.getReceiver().equals(user.getId())){
          throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);
        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
