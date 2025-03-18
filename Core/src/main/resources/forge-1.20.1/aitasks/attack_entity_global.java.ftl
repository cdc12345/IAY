<#include "aiconditions.java.ftl">
this.targetSelector.addGoal(${cbi+1}, new NearestAttackableTargetGoal<>(this, ${generator.map(field$entity, "entities")}.class,10, ${field$insight?lower_case},
        ${field$nearby?lower_case},a -> TargetFilterEvent.postEvent(1, a, HelloEntityEntity.this).getResult() == Event.Result.ALLOW)<@conditionCode field$condition/>);