package life.gjj.community.community.service;

import life.gjj.community.community.dto.NotificationDTO;
import life.gjj.community.community.dto.PaginationDTO;
import life.gjj.community.community.enums.NotificationTypeEnum;
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
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        if (notifications.size()==0){
            return paginationDTO;
        }
        List<NotificationDTO> notificationDTOS=new ArrayList();
        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setType(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        paginationDTO.setData(notificationDTOS);
        return paginationDTO;
    }

    public Long underadCount(Long userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId);
       return notificationMapper.countByExample(notificationExample);
    }
}
