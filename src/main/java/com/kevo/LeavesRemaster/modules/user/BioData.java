package com.kevo.LeavesRemaster.modules.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BioData {
    private String first_name;
    private String last_name;
    private String full_name;
    private String profile_image;
    private Long user_id;
    private Boolean deleted;

    public User updateUserWithBio(User user){
        user.setFirstName(first_name);
        user.setLastName(last_name);
        user.setFullName(full_name);
        user.setProfileImage(profile_image);
        user.setDeleted(deleted);
        return user;
    }

    public User createUserFromBio() {
        return User.builder()
                .userId(user_id)
                .firstName(first_name)
                .lastName(last_name)
                .fullName(full_name)
                .profileImage(profile_image)
                .deleted(deleted)
                .build();
    }

}
