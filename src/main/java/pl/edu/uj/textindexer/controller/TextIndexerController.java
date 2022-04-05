package pl.edu.uj.textindexer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.uj.textindexer.service.TextIndexerService;

@RestController
class TextIndexerController {

  @Autowired
  TextIndexerService textIndexerService;

  @RequestMapping("/index")
  public @ResponseBody
  String getIndex(@RequestParam(value = "text") String text) {

    return textIndexerService.printIndex(text);
  }
}

