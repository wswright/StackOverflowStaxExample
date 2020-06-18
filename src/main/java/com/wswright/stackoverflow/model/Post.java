package com.wswright.stackoverflow.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;
@Table("Posts")
@Data
public class Post {
    @Id
    @Column(value="Id")
    private Long id;
    @Column(value="PostTypeId")
    private Integer postTypeId;
    @Column(value="AcceptedAnswerId")
    private Integer acceptedAnswerId;
    @Column(value="CreationDate")
    private Date creationDate;
    @Column(value="Score")
    private String score;
    @Column(value="ViewCount")
    private Long viewCount;
    @Column(value="Body")
    private String body;
    @Column(value="OwnerUserId")
    private String ownerUserId;
    @Column(value="LastEditorUserId")
    private String lastEditorUserId;
    @Column(value="LastEditorDisplayName")
    private String lastEditorDisplayName;
    @Column(value="LastEditDate")
    private Date lastEditDate;
    @Column(value="LastActivityDate")
    private Date lastActivityDate;
    @Column(value="Title")
    private String title;
    @Column(value="Tags")
    private String tags;
    @Column(value="AnswerCount")
    private Integer answerCount;
    @Column(value="CommentCount")
    private Integer commentCount;
    @Column(value="FavoriteCount")
    private Integer favoriteCount;
    @Column(value="CommunityOwnedDate")
    private Date communityOwnedDate;
    @Column(value="ParentId")
    private String parentId;
    @Column(value="OwnerDisplayName")
    private String ownerDisplayName;
    @Column(value="ClosedDate")
    private Date closedDate;
}

