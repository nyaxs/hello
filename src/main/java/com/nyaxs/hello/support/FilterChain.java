package com.nyaxs.hello.support;

/**
 * @author liuyh3@belink.com
 * @version v1.0
 * @title
 * @description
 * @date 2023-03-07 13:59
 */
@FunctionalInterface
public interface FilterChain {
    public void addFilter(String s);
}
