package life.gjj.community.community.schedule;

import life.gjj.community.community.cache.HotTagCache;
import life.gjj.community.community.mapper.QuestionMapper;
import life.gjj.community.community.model.Question;
import life.gjj.community.community.model.QuestionExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Slf4j
public class HotTagTasks {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
@Autowired
QuestionMapper questionMapper;
@Autowired
HotTagCache hotTagCache;
    @Scheduled(fixedRate = 20000)
//    @Scheduled(cron = "0 0 1 * * *")
    public void reportCurrentTime() {
        int offset=0;
        int limit=20;
        List<Question> list=new ArrayList();
        Map<String,Integer> priorities=new HashMap<>();
        while (offset==0||list.size()==20){
            list=questionMapper.selectByExampleWithBLOBsWithRowbounds(new QuestionExample(), new RowBounds(offset, limit));
            for (Question question:list){
                String[] tags = StringUtils.split(question.getTag(), ",");
                for(String tag:tags){
                    Integer priority = priorities.get(tag);
                    if (priority!=null){
                        priorities.put(tag,priority+5+question.getCommentCount());
                    }else {
                        priorities.put(tag,5+question.getCommentCount());
                    }
                }
            }
            offset+=limit;
        }
        hotTagCache.updateTags(priorities);
    }
}
