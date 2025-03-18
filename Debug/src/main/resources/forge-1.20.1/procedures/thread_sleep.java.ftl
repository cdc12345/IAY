try {
    Thread.sleep(${input$value});
} catch (InterruptedException e) {
    throw new RuntimeException(e);
}