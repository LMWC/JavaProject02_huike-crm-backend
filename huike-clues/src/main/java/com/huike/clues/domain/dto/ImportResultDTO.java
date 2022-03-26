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


    public static ImportResultDTO error(){
        return new ImportResultDTO(0,1);
    }

    public static ImportResultDTO success(){
        return new ImportResultDTO(1,0);
    }

    public ImportResultDTO addAll(ImportResultDTO data){
        this.failureNum+=data.getFailureNum();
        this.successNum+=data.getSuccessNum();
        return this;
    }
}
