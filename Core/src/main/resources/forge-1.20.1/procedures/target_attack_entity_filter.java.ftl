targetSelector.addGoal(${input$priority}, new NearestAttackableTargetGoal<>(mob, ${generator.map(field$entity, "entities")}.class,10, ${field$insight?lower_case},
        ${field$nearby?lower_case},a -> {
                Entity entityiterator = a;
                try {
                        ${statement$filter}
                } catch (Exception ignore){
                        return false;
                }
                return true;
        }));