package life.gjj.community.community.service;

import life.gjj.community.community.mapper.AdMapper;
import life.gjj.community.community.model.Ad;
import life.gjj.community.community.model.AdExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdService {
    @Autowired
    AdMapper adMapper;
    public List<Ad> list(){

        AdExample navExample = new AdExample();
        navExample.createCriteria().andStatusEqualTo(1)
                .andGmtStartLessThan(System.currentTimeMillis())
                .andGmtEndGreaterThan(System.currentTimeMillis());
//        navExample.setOrderByClause("priority desc");
        return adMapper.selectByExample(navExample);

    }
}
