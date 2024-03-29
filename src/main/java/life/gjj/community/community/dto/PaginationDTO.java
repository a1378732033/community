package life.gjj.community.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class PaginationDTO<T> {
    List<T> data;
    boolean showPrevious;//上一页
    boolean showFirstPage;//首页
    boolean showNext;//下一页
    boolean showEndPage;//尾页
    Integer page;
    List<Integer> pages=new ArrayList();
    Integer totalPage;
    public void setPagintion(Integer totalPage, Integer page) {
        this.page=page;
        this.totalPage=totalPage;
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
