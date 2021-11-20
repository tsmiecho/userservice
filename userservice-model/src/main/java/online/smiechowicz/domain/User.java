package online.smiechowicz.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class User {
    String id;
    String name;
    String email;
}
