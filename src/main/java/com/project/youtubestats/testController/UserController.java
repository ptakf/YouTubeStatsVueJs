package com.project.youtubestats.testController;


import com.project.youtubestats.testDataTypeObjects.DataResult;
import com.project.youtubestats.testDataTypeObjects.UserData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//gggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg
@RestController
@RequestMapping("/user")

public class UserController {

  @PostMapping("/data")
  public ResponseEntity<DataResult> user (@RequestBody UserData userData){
    System.out.println("email: "+ userData.getEmail());
    return  ResponseEntity.ok(new DataResult("user data"));
  }

  @GetMapping("/test")
  public ResponseEntity<DataResult> test() {
    return  ResponseEntity.ok(new DataResult("test data"));
  }

}
