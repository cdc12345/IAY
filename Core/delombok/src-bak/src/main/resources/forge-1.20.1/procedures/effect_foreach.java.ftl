if (${input$entity} instanceof LivingEntity _live${cbi}){
    for (Object objectiterator: _live${cbi}.getActiveEffects().stream().map(MobEffectInstance::getEffect).toList()) {
        ${statement$foreach}
    }
}