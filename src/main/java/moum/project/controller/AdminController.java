package moum.project.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import moum.project.service.AchievementService;
import moum.project.service.BoardService;
import moum.project.service.CollectionCategoryService;
import moum.project.service.UserService;
import moum.project.vo.Achievement;
import moum.project.vo.Board;
import moum.project.vo.Maincategory;
import moum.project.vo.Subcategory;
import moum.project.vo.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * packageName    : moum.project.controller
 * fileName       : AdminController
 * author         : narilee
 * date           : 24. 10. 28.
 * description    : 관리자 페이지를 처리하는 컨트롤러입니다.
                    관리자는 admin 설정이 1이거나 true 일때 접근 가능합니다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 10. 28.        narilee       최초 생성
 */
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

  private final UserService userService;
  private final BoardService boardService;
  private final CollectionCategoryService categoryService;
  private final AchievementService achievementService;

  /**
   * 이 메서드는 "/admin/management" URL로 들어오는 GET 요청을 처리합니다.
   *
   * @return "management" 뷰 이름을 반환합니다.
   */
  @GetMapping("management")
  public void management(Model model) {

  }

  @GetMapping("management/board/list")
  public String boardlist(Model model) {
    return "admin/board/list";
  }

  @GetMapping("/user/list")
  @ResponseBody
  public List<User> listUser(int pageNo, int pageCount) throws Exception {
    return userService.listByPage((pageNo - 1) * pageCount, pageCount);
  }

  @GetMapping("/board/list")
  @ResponseBody
  public List<Board> listBoard(int pageNo, int pageCount) throws Exception {
    return boardService.listByPage((pageNo - 1) * pageCount, pageCount);
  }

  @GetMapping("/category/list")
  @ResponseBody
  public List<Maincategory> listCategory(int pageNo, int pageCount) throws Exception {
    return categoryService.listMaincategoryByPage((pageNo - 1) * pageCount, pageCount);
  }

  @GetMapping("/achievement/list")
  @ResponseBody
  public List<Achievement> listAchievement(int pageNo, int pageCount) throws Exception {
    return achievementService.listByPage((pageNo - 1) * pageCount, pageCount);
  }

  @GetMapping("/user/count")
  @ResponseBody
  public int countUser() throws Exception {
    return userService.count();
  }

  @GetMapping("/board/count")
  @ResponseBody
  public int countBoard() throws Exception {
    return boardService.count();
  }

  @GetMapping("/category/count")
  @ResponseBody
  public int countCategory() throws Exception {
    return categoryService.count();
  }

  @GetMapping("/achievement/count")
  @ResponseBody
  public int countAchievement() throws Exception {
    return achievementService.count();
  }

  @GetMapping("/user")
  @ResponseBody
  public User getUser(int no) throws Exception {
    return userService.get(no);
  }

  @GetMapping("/board")
  @ResponseBody
  public Board getBoard(int no) throws Exception {
    return boardService.get(no);
  }

  @GetMapping("/category")
  @ResponseBody
  public List<Subcategory> getCategory(int no) throws Exception {
    return categoryService.listSubcategoryByMaincategory(no);
  }

  @GetMapping("/achievement")
  @ResponseBody
  public Achievement getAchievement(String no) throws Exception {
    return achievementService.get(no);
  }

  @GetMapping("/updateAdmin")
  @ResponseBody
  public String updateAdmin(
      boolean admin,
      int userNo,
      @AuthenticationPrincipal UserDetails userDetails) throws Exception {

    User loginUser = userService.getByEmail(userDetails.getUsername());
    if (loginUser.getNo() == userNo) {
      return "inhibited";
    }

    if (userService.updateAdmin(admin, userNo)) {
      return "success";
    } else {
      return "failure";
    }
  }
}
