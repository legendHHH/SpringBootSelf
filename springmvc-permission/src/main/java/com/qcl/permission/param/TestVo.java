package com.qcl.permission.param;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 测试校验
 *
 * @author legend
 * @version 1.0
 * @description
 * @date 2020/9/25
 */
@Getter
@Setter
public class TestVo {

    @NotNull(message = "id不可以为空")
    @Max(value = 10, message = "id 不能大于10")
    @Min(value = 0, message = "id 至少大于等于0")
    private Integer id;

    @NotBlank
    private String msg;

    /**
     * 集合
     */
    private List<String> str;
}
