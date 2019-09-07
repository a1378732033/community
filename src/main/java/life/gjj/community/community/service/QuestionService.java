package life.gjj.community.community.service;

import life.gjj.community.community.dto.PaginationDTO;
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
    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalcount = questionMapper.count();
        Integer totalPage;
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
        List<Question> questions= questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList=new ArrayList();
        for (Question question:questions){
          User user =userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
       questionDTOList.add(questionDTO);
        }
        paginationDTO.setQustions(questionDTOList);


        return paginationDTO;
    }

    public  PaginationDTO list(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        Integer totalcount = questionMapper.countByUserId(userId);
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
        List<Question> questions= questionMapper.listByUserId(userId,offset,size);
        List<QuestionDTO> questionDTOList=new ArrayList();
        for (Question question:questions){
            User user =userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQustions(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
      Question question=questionMapper.getById(id);
      QuestionDTO questionDTO=new QuestionDTO();
      BeanUtils.copyProperties(question,questionDTO);
      User user =userMapper.findById(question.getCreator());
      questionDTO.setUser(user);
      return  questionDTO;
    }
}
