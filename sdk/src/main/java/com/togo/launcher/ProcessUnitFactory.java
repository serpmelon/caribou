package com.togo.launcher;

/**
 * @Author taiyn
 * @Description factory
 * @Date 5:58 下午 2020/6/30
 **/
public class ProcessUnitFactory {

    public ProcessUnit createProcessUnit(ProcessUnitRecipe recipe) {

        return recipe.cook();
    }
}
