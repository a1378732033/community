package life.gjj.community.community.service;

import life.gjj.community.community.dto.PaginationDTO;
import life.gjj.community.community.dto.QuestionDTO;
import life.gjj.community.community.dto.QuestionQueryDTO;
import life.gjj.community.community.exception.CustomizeErrorCode;
import life.gjj.community.community.exception.CustomizeException;
import life.gjj.community.community.mapper.QuestionExtMapper;
import life.gjj.community.community.mapper.QuestionMapper;
import life.gjj.community.community.mapper.UserMapper;
import life.gjj.community.community.model.Question;
import life.gjj.community.community.model.QuestionExample;
import life.gjj.community.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionExtMapper questionExtMapper;
    public PaginationDTO list( String search, String tag,Integer page, Integer size) {
        if (StringUtils.isNotBlank(search)){
            String[] tags = StringUtils.split(search, " ");
            search = Arrays.stream(tags).collect(Collectors.joining("|"));
        }
        PaginationDTO paginationDTO = new PaginationDTO();

        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);
        questionQueryDTO.setTag(tag);
        Integer totalcount = questionExtMapper.countBySearch(questionQueryDTO);
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
        Integer offset=page<1?0:size*(page-1);
        questionQueryDTO.setPage(offset);
        questionQueryDTO.setSize(size);
        List<Question> questions = questionExtMapper.selectBysearch(questionQueryDTO);
        List<QuestionDTO> questionDTOList=new ArrayList();
        for (Question question:questions){
          User user =userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
       questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);
        return paginationDTO;
    }

    public  PaginationDTO list(Long userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;

        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        Integer totalcount = (int) questionMapper.countByExample(questionExample);
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
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(example, new RowBounds(offset, size));
        List<QuestionDTO> questionDTOList=new ArrayList();
        for (Question question:questions){
            User user =userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO getById(Long id) {
      Question question=questionMapper.selectByPrimaryKey(id);
    if (question==null){
      throw  new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
    }
      QuestionDTO questionDTO=new QuestionDTO();
      BeanUtils.copyProperties(question,questionDTO);
      User user =userMapper.selectByPrimaryKey(question.getCreator());
      questionDTO.setUser(user);
      return  questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId()==null){
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(System.currentTimeMillis());
            question.setCommentCount(0);
            question.setLikeCount(0);
            question.setViewCount(0);
            questionMapper.insert(question);
        }else{
     //更新
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(updateQuestion, example);
            if (updated!=1){
               throw  new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }
    public void incView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }

    public List<QuestionDTO> selectRealated(QuestionDTO queryDTO) {
        if (StringUtils.isBlank(queryDTO.getTag())){
         return new ArrayList<>();
        }
        String[] tags = StringUtils.split(queryDTO.getTag(), ",");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexpTag);
        List<Question> questions = questionExtMapper.selectRelate(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOS;
    }
}
