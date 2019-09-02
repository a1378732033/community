package life.gjj.community.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class PaginationDTO {
    List<QuestionDTO> qustions;
    boolean showPrevious;//上一页
    boolean showFirstPage;//首页
    boolean showNext;//下一页
    boolean showEndPage;//尾页

    Integer page;
    List<Integer> pages=new ArrayList();
    Integer totalPage;
    public void setPagintion(Integer totalcount, Integer page, Integer size) {
        this.page=page;
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
        pages.add(page);
        for (int i=1;i<=3;i++){
            if(page-i>0){
                pages.add(0,page-i);
            }
            if(page+i<=totalPage){
                pages.add(page+i);
            }
        }
        //是否有上一页
        if (page==1){
            showPrevious=false;
        }else {
            showPrevious=true;
            //是否有下一页
        }if(page==totalPage){
            showNext=false;
        }else {
            showNext=true;
        }
        //是否有首页
        if(pages.contains(1)){
            showFirstPage=false;
        }else {
            showFirstPage=true;
        }
        if (pages.contains(totalPage)){
            showEndPage=false;
        }else {
            showEndPage=true;
        }
    }
}
