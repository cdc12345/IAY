targetSelector.addGoal(${input$priority}, new NearestAttackableTargetGoal(mob, ${generator.map(field$entity, "entities")}.class, ${field$insight?lower_case},
${field$nearby?lower_case}));