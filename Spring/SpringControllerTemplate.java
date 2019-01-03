// package com.mypackage.awesomeapp

@Controller
public class SpringControllerTemplate {

    @Autowired
    MyClass myClass;

    @ResourceMapping("/somePath/{id}")
    public String redirectToPath(@PathVariable("id") Integer id) {
        // do something here and redirect to file {prefix}/viewMyPage.{suffix}
        return "viewMyPage";
    }
}