package com.briefing_management.summary.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Summary {
    String articleId;
    String title;
    String summary;
    String kw;
    String publishTime;
}
