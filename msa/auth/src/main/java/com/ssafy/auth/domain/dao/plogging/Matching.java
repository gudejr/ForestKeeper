package com.ssafy.auth.domain.dao.plogging;

import com.ssafy.auth.domain.dao.BaseEntity;
import com.ssafy.auth.domain.dao.mountain.Mountain;
import com.ssafy.auth.domain.dao.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Matching extends BaseEntity {

    @Column(name = "match_title")
    private String title;

    @Column(name = "match_content")
    @Lob
    private String content;

    @Column(name = "match_create_time")
    private LocalDateTime createTime;

    @Column(name = "plogging_date")
    private LocalDate ploggingDate;

    @Column(name = "views")
    private long views;

    @Column(name = "is_closed")
    private boolean isClosed;

    @Column(name = "total")
    private int total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "matching_is_deleted")
    private boolean delete;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mountain_code", referencedColumnName = "mountain_code")
    private Mountain mountain;

    @OneToMany(mappedBy = "matching", fetch = FetchType.LAZY)
    private List<MatchingUser> matchingUsers = new ArrayList<>();

    //     글 수정
    public void changeMatch(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 조회수 증가
    public void increaseViews() {
        this.views += 1;
    }

    public void changeDelete() {
        this.delete = true;
    }

    public void changeMatching(String title, String content, LocalDate ploggingDate, int total,
        Mountain mountain) {
        this.title = title;
        this.content = content;
        this.ploggingDate = ploggingDate;
        this.total = total;
        this.mountain = mountain;
    }
}
