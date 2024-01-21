package com.project.youtubestats.dataTypeObjects;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
class SubscriberCountMetrics {

  //json string with observations
  //kinda
  //[ {
  //    "date_in_string": 123,
  //    "date_in_string": 123,
  //    "date_in_string": 123,
  //    "date_in_string": 123
  //  }
  //    ]
  private String observations;
}
