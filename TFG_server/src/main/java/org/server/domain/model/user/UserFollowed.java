package org.server.domain.model.user;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class UserFollowed {
    private int id;
    private User owner;
    private User followed;
}
