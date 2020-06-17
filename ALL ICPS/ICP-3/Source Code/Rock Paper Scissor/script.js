
(function() {
    const buttons = document.querySelectorAll('.button');
    const computer = document.querySelector('.computer');
    const output = document.querySelector('.output');

    // The keys contains what the key wins over
    const combinations = {
        rock: 'scissors',
        paper: 'rock',
        scissors: 'paper'
    };
    // Get keys of object to be able to randomly select one
    const keys = Object.keys(combinations);
    const waitTime = 50;
    const scrambleCount = 20;

    // Use promise to wait
    const wait = ms => new Promise(resolve => setTimeout(resolve, ms));

    const calculateWinner = (playerChoice, computerChoice) => {
        if (combinations[playerChoice] === computerChoice) {
            output.innerHTML = 'You are the winner!';
        } else if (playerChoice === computerChoice) {
            output.innerHTML = 'Tie!';
        } else {
            output.innerHTML = 'The computer is the winner!';
        }
    };

    const compMove = async playerChoice => {
        let computerPick = null;
        for (let i = 0; i < scrambleCount; i++) {
            await wait(waitTime);
            computerPick = keys[Math.floor(Math.random() * keys.length)];
            computer.src = `${computerPick}.png`;
        }
        calculateWinner(playerChoice, computerChoice);
    };


    const handleClick = e => {
        // Reset all buttons
        buttons.forEach(button => (button.classList = 'button'));
        // Set class on selected button
        e.target.classList = 'button selected';
        const playerChoice = e.target.dataset.type; // rock, paper or scissors from the data attribute
        compMove(playerChoice);
    };


    buttons.forEach(button => {
        button.addEventListener('click', handleClick);
    });
})();
