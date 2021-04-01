package com.pegasus.messengerserver.repository;

import com.pegasus.messengerserver.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
