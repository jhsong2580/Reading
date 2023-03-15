package study;


public class TobyApplication {
//
//    @Bean
//    public ServletWebServerFactory servletWebServerFactory() {
//        return new TomcatServletWebServerFactory();
//    }
//
//    @Bean
//    public DispatcherServlet dispatcherServlet() {
//        return new DispatcherServlet() {
//            @Override
//            public void setApplicationContext(ApplicationContext applicationContext) {
//                super.setApplicationContext(applicationContext);
//                System.out.println(applicationContext);
//            }
//        };
//    }
//
//
//    private static AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {
//        @Override
//        protected void onRefresh() {
//            super.onRefresh();
//            ServletWebServerFactory tomcatServletWebServerFactory = this.getBean(ServletWebServerFactory.class);
//            DispatcherServlet dispatcherServlet = this.getBean(DispatcherServlet.class);
////            dispatcherServlet.setApplicationContext(this); // 이 코드가 없어도, ApplicationContext Setter에 의해 Setter 주입이 일어난다.
//
//            WebServer webServer = tomcatServletWebServerFactory.getWebServer(
//                servletContext -> servletContext.addServlet("dispatcherServlet", dispatcherServlet)
//                    .addMapping("/*")
//            );// servlet container를 만드는 실직적 함수
//            webServer.start();
//        }
//    };
//
//    public static void main(String[] args) {
//        applicationContext.register(TobyApplication.class);
//
//        applicationContext.refresh();
//
//    }
//
//    private static ServletContextInitializer getDispatcherServletInfo(WebApplicationContext applicationContext) {
//        ServletContextInitializer servletContextInitializer =
//            (servletContext) -> {
//                servletContext.addServlet("dispatcherServlet",
//                    new DispatcherServlet(applicationContext)
//                ).addMapping("/*");
//            };
//
//        return servletContextInitializer;
//    }

}
