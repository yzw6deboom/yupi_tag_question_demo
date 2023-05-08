package com.sans.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usage {
    String prompt_tokens;
    String completion_tokens;
    String total_tokens;
}
