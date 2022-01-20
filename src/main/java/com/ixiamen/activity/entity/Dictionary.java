package com.ixiamen.activity.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author luoyongbin
 * @since 2020-04-28
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("tb_dictionary")
@ApiModel(value = "字典对象")
public class Dictionary extends Model<Dictionary> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 字典编码
     */
    @ApiModelProperty(value = "字典编码")
    @TableField("dictionary_code")
    private String dictionaryCode;
    /**
     * 字典名称
     */
    @ApiModelProperty(value = "字典名称")
    @TableField("dictionary_name")
    private String dictionaryName;
    /**
     * 字典值
     */
    @ApiModelProperty(value = "字典值")
    @TableField("dictionary_value")
    private String dictionaryValue;
    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    @TableField("dictionary_type")
    private String dictionaryType;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    @TableField("dictionary_des")
    private String dictionaryDes;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
