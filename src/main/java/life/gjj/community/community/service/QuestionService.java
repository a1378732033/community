package life.gjj.community.community.service;

import life.gjj.community.community.dto.QuestionDTO;
import life.gjj.community.community.mapper.QuestionMapper;
import life.gjj.community.community.mapper.UserMapper;
import life.gjj.community.community.model.Question;
import life.gjj.community.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;
    public List<QuestionDTO> list() {

        List<Question> questions= questionMapper.list();
        List<QuestionDTO> questionDTOList=new ArrayList();
        for (Question question:questions){
          User user =userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
       questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
}
