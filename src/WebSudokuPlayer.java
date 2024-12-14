import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class WebSudokuPlayer {
  private final ChromeDriver driver;
  private final WebElement webBoard;
  private SudokuBoard board;

  public WebSudokuPlayer() {
    driver = new ChromeDriver();
    driver.get("https://west.websudoku.com/");
    webBoard = driver.findElement(By.id("puzzle_grid"));
  }

  public WebSudokuPlayer(int level) {
    driver = new ChromeDriver();
    driver.get("https://west.websudoku.com/?level=" + level);
    webBoard = driver.findElement(By.id("puzzle_grid"));
  }

  public void solve() throws InterruptedException {
    SudokuBoard solvedBoard = new SudokuBoard(this.getBoard());
    solvedBoard.solveRecursively();
    int[][] solutionData = solvedBoard.getIntBoard();
    System.out.println("Board solved!");
    int move = 1;

    while (board.getBoard().peek().getCandidateAmount() != 10) {
      System.out.println("Making move #" + move);
      int x = board.getBoard().peek().getX() - 1;
      int y = board.getBoard().peek().getY() - 1;
      makeMove(solutionData[y][x]);
      move++;
      Thread.sleep(100);
    }
  }

  public void solveIteratively() throws InterruptedException {
    int move = 1;
    while (board.getBoard().peek().getCandidateAmount() != 10) {
      System.out.println("Making move #" + move);
      makeMove();
      move++;
      Thread.sleep(100);
    }
  }

  public void makeMove() {
    int[] moveData = board.makeMove();
    WebElement cellInput = webBoard.findElement(By.id(getCellId(moveData[0], moveData[1])));
    cellInput.sendKeys(String.valueOf(moveData[2]));
  }

  public void makeMove(int numToPlace) {
    int[] moveData = board.makeMove(numToPlace);
    WebElement cellInput = webBoard.findElement(By.id(getCellId(moveData[0], moveData[1])));
    cellInput.sendKeys(String.valueOf(moveData[2]));
  }

  public void initializeBoard() {
    List<WebElement> rows = webBoard.findElements(By.tagName("tr"));
    board = new SudokuBoard();
    for(int row = 0; row < 9; row++) {
      List<WebElement> cells = rows.get(row).findElements(By.tagName("td"));

      for(int cell = 0; cell < 9; cell++) {
        WebElement inputElement = cells.get(cell).findElement(By.tagName("input"));
        if (inputElement.getDomAttribute("readonly") != null) {
          int val = Integer.parseInt(inputElement.getDomProperty("value"));
          board.tryChangeTile(cell + 1, row + 1, val);
        }
      }
    }

    System.out.println("Board loaded: \n" + board);
  }

  public static String getCellId(int x, int y) {
    return "f" + (x - 1) + (y - 1);
  }

  public SudokuBoard getBoard() {
    return board;
  }

  public ChromeDriver getDriver() {
    return driver;
  }
}
