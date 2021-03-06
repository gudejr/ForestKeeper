package com.ssafy.api.domain.dao.community;

import com.ssafy.api.domain.dao.BaseEntity;
import com.ssafy.api.domain.dao.mountain.Mountain;
import com.ssafy.api.domain.dao.user.User;
import com.ssafy.api.domain.enums.CommunityCode;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Community extends BaseEntity {

    @Column(name = "community_code")
    private CommunityCode communityCode;

    @Column(name = "community_title")
    private String title;

    @Column(name = "community_description")
    @Lob
    private String description;

    @Column(name = "community_create_time")
    private LocalDateTime createTime;

    @Column(name = "community_views")
    private long views;

    @Column(name = "community_is_deleted")
    private boolean delete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mountain_id")
    private Mountain mountain;

    // 글 수정
    public void changeCommunity(String title, String description) {
        this.title = title;
        this.description = description;
    }

    // 조회수 증가
    public void increaseViews() {
        this.views += 1;
    }

    // 댓글 삭제
    public void changeDelete() {
        this.delete = true;
    }

}
