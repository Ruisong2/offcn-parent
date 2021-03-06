package com.offcn.order.vo.resp;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TReturn {
    private Integer id;

    private Integer projectid;

    private String type;

    private Integer supportmoney;

    private String content;

    private Integer count;

    private Integer signalpurchase;

    private Integer purchase;

    private Integer freight;

    private String invoice;

    private Integer rtndate;
}
