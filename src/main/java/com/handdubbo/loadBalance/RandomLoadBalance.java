package com.handdubbo.loadBalance;

import java.util.List;
import java.util.Random;

/**
 * @author: sxx
 * @Date 2020/6/26 15:54
 * @Version 1.0
 **/
public class RandomLoadBalance {

    /**
     * 随机一个provider
     * @param providerList provider列表
     * @return provider
     */
    public String doSelect(List<String> providerList) {
        int size = providerList.size();
        Random random = new Random();
        return providerList.get(random.nextInt(size));
    }
}
