package objects.command;

import static objects.Util.*;

public class DrawCommand extends Command{

	@Override
	public void execute() throws CommandException {
		winner = null;
		gameOver = true;
	}
}
