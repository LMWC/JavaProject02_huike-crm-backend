package com.huike.clues.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 线索导入结果集对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImportResultDTO {

    //成功数量
    private Integer successNum;

    //失败数量
    private Integer failureNum;

    /**
     * 提供静态方法，发生插入失败的时候记录
     * @return
     */
    public static ImportResultDTO error(){
        return new ImportResultDTO(0,1);
    }

    /**
     * 提供静态方法，发生成功的时候记录数据
     * @return
     */
    public static ImportResultDTO success(){
        return new ImportResultDTO(1,0);
    }

    /**
     * 该方法主要是将每次插入的结果与总的结果进行汇总时进行调用
     * @param data
     * @return
     */
    public ImportResultDTO addAll(ImportResultDTO data){
        this.failureNum+=data.getFailureNum();
        this.successNum+=data.getSuccessNum();
        return this;
    }
}
