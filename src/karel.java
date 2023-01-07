import stanford.karel.*;
public class BlankKarel extends SuperKarel {
    int x_step = 0;
    int y_step = 0;
    int xAxisMoves = 0;
    int yAxisMoves = 0;
    int counter = 0;

    public void run() {
        setBeepersInBag(1000);
        getCoordinates();
        System.out.println("(" + x_step + "," + y_step + ")");
        divideIntoEqualChampers(x_step, y_step);
        System.out.println(counter);
    }
    private void getCoordinates() {
        moveToWallX_axis();
        turnLeft();
        if (frontIsBlocked()) {
            turnLeft();
            moveToWall();
            turnAround();
            y_step++;
        } else {
            moveToWallY_axis();
            turnLeft();
            if (frontIsBlocked()) {
                turnLeft();
                pickBeepersToWall();
                turnLeft();
            }
        }
    }
    private void divideIntoEqualChampers(int x_step, int y_step) {
        if (x_step % 2 == 0 && y_step % 2 == 0) {
            if (x_step > 2 && y_step > 2) {
                collectBeepers((y_step / 2) - 1);
                turnLeft();
                move();
                turnLeft();
                evenByEven(x_step, y_step);
            } else if (x_step == 2 && y_step == 2) {
                collectBeepers((y_step / 2) - 1);
                diagonalMove();
            }
        } else if (x_step % 2 != 0 && y_step % 2 != 0) {
            collectBeepers((y_step / 2));
            turnAround();
            oddByOdd(x_step, y_step);
        } else if (x_step % 2 == 0 && y_step % 2 != 0) {
            if(y_step > 1) {
                collectBeepers((y_step / 2));
                turnAround();
                EvenByOdd(x_step, y_step);
            }
            else if(y_step == 1){
                divideIntoChambersIfXOrYisOneInEven(x_step);
            }
        }
        else if (x_step % 2 != 0 && y_step % 2 == 0) {
            if (x_step > 1) {
                collectBeepers((y_step / 2) - 1);
                turnLeft();
                move();
                turnLeft();
                oddByEven(x_step, y_step);
            } else if (x_step == 1) {
                turnLeft();
                divideIntoChambersIfXOrYisOneInEven(y_step);
            }
        }
    }
    private void moveToWallX_axis() {
        x_step++;
        while (frontIsClear()) {
            move();
            x_step++;
            counter++;
        }
    }
    private void moveToWallY_axis() {
        move();
        y_step++;
        while (frontIsClear()) {
            move();
            y_step++;
            counter++;
        }
        y_step++;
    }
    private void moveToWall() {
        while (frontIsClear()) {
            move();
            counter++;
        }
    }
    private void pickBeepersToWall() {
        if (beepersPresent()) {
            pickBeeper();
        }
        while (frontIsClear()) {
            move();
            if (beepersPresent()) {
                pickBeeper();
            }
        }
    }
    private void moveToSpecificArea(int area) {
        for (int i = 1; i <= area; i++) {
            move();
            counter++;
        }
    }
    private void collectBeepers(int movesToCollect) {
        for (int i = 1; i <= movesToCollect; i++) {
            pickBeepersToWall();
            turnLeft();
            move();
            turnLeft();
            pickBeepersToWall();
            jumpToSecondPassToFaceOppositeDirection();
            pickBeepersToWall();
        }
    }
    private void evenByEven(int x, int y) {
        xAxisMoves = (x / 2) - 1;
        yAxisMoves = (y / 2) - 1;
        moveToSpecificArea(xAxisMoves);
        turnLeft();
        putBeepersBySpecificMoves(yAxisMoves);
        turnLeft();
        fillEvenSidesOrUpward(xAxisMoves);
        turnLeft();
        fillEvenSidesOrUpward(yAxisMoves);
        turnLeft();
        fillEvenSidesOrUpward(xAxisMoves);
        turnLeft();
        putBeepersBySpecificMoves(yAxisMoves);
        BackToStart();
    }
    private void oddByOdd(int x, int y) {
        xAxisMoves = (x / 2);
        yAxisMoves = (y / 2);
        moveToSpecificArea(xAxisMoves);
        turnLeft();
        putBeepersBySpecificMoves((y - 1));
        turnLeft();
        moveToWall();
        turnLeft();
        moveToSpecificArea(yAxisMoves);
        turnLeft();
        putBeepersBySpecificMoves((x - 1));
        turnRight();
        moveToWall();
        BackToStart();
    }
    private void EvenByOdd(int x, int y) {
        xAxisMoves = x / 2 - 1;
        yAxisMoves = y / 2;
        moveToSpecificArea(xAxisMoves);
        turnLeft();
        putBeepersBySpecificMoves(yAxisMoves);
        turnLeft();
        putBeepersBySpecificMoves(xAxisMoves);
        turnAround();
        moveToSpecificArea(xAxisMoves);
        turnLeft();
        fillEvenSidesOrUpward(yAxisMoves);
        turnLeft();
        putBeepersBySpecificMoves(xAxisMoves);
        turnAround();
        moveToSpecificArea(xAxisMoves);
        turnLeft();
        putBeepersBySpecificMoves(yAxisMoves);
        BackToStart();
    }
    private void oddByEven(int x, int y) {
        xAxisMoves = (x / 2);
        yAxisMoves = (y / 2) - 1;
        moveToSpecificArea(xAxisMoves);
        turnLeft();
        putBeepersBySpecificMoves(yAxisMoves);
        turnLeft();
        fillEvenSidesOrUpward(xAxisMoves);
        turnLeft();
        putBeepersBySpecificMoves(xAxisMoves);
        turnAround();
        moveToSpecificArea(xAxisMoves);
        turnLeft();
        fillEvenSidesOrUpward(xAxisMoves);
        turnLeft();
        moveToWall();
        BackToStart();
    }
    private void putBeepersBySpecificMoves(int moves) {
        if (noBeepersPresent()) {
            putBeeper();
        }
        for (int i = 1; i <= moves; i++) {
            counter++;
            move();
            if (noBeepersPresent()) {
                putBeeper();
            }
        }
    }
    private void jumpToSecondPassToFaceOppositeDirection() {
        counter++;
        turnRight();
        move();
        turnRight();
    }
    private void diagonalMove() {
        move();
        turnLeft();
        move();
        turnLeft();
        putBeeper();
        move();
        turnLeft();
        move();
        putBeeper();
    }
    private void fillEvenSidesOrUpward(int Moves) {
        putBeepersBySpecificMoves(Moves);
        jumpToSecondPassToFaceOppositeDirection();
        putBeepersBySpecificMoves(Moves);
    }
    private void BackToStart() {
        turnRight();
        moveToWall();
        turnAround();
    }
    private void divideIntoChambersIfXOrYisOneInEven(int steps){
        moveToSpecificArea(y_step / 2 - 1);
        putBeeper();
        move();
        counter++;
        putBeeper();
        turnAround();
        moveToWall();
        turnAround();
    }
}