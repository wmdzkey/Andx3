package com.umk.andx3.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gx
 * 注意本注解，只有继承了BaseActivity才能使用。
 * 若没有继承BaseActiviy，使用HttpInterface.proxy(Context,Class)初始化
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Api {
}
