package moum.project.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import moum.project.service.ChatService;
import moum.project.service.UserService;
import moum.project.vo.Chat;
import moum.project.vo.Chatroom;
import moum.project.vo.User;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

  private final ChatService chatService;
  private final UserService userService;

  @MessageMapping("/send/{roomNo}")
  @SendTo("/chat/receive/{roomNo}")
  public Chat sendMessage(
      @DestinationVariable int roomNo,
      Chat chat,
      @AuthenticationPrincipal UserDetails userDetails) throws Exception {

    System.out.println("sendMessage 실행!");

    User loginUser = userService.getByEmail(userDetails.getUsername());
    Chatroom chatroom = chatService.getRoom(roomNo);

    if (loginUser.getNo() == chatroom.getParticipant().getNo()
        || loginUser.getNo() == chatroom.getBoard().getUserNo()) {

      chat.setChatroom(chatroom);
      chat.setSender(loginUser);
      return chat;

    } else {
      throw new Exception("잘못된 접근입니다.");
    }
  }

  @GetMapping("/listRoom")
  @ResponseBody
  public List<Chatroom> listRoom(@AuthenticationPrincipal UserDetails userDetails) throws Exception {

    User loginUser = userService.getByEmail(userDetails.getUsername());

    return chatService.listRoom(loginUser.getNo());
  }

  @GetMapping("/openRoom")
  @ResponseBody
  public Chatroom openRoom(int no) throws Exception {
    return chatService.getRoom(no);
  }

  @GetMapping("/loadChat")
  @ResponseBody
  public List<Chat> loadChat(int no, int pageNo) throws Exception {
    return chatService.loadChat(no, (pageNo - 1) * 20, 20);
  }
}
