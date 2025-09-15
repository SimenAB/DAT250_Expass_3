<script>
    const API_BASE = import.meta.env.DEV ? "http://localhost:8080" : "";

    // users
    let username = "";
    let email = "";
    let users = [];

    async function createUser() {
        if (!username || !email) return;
        const res = await fetch(`${API_BASE}/users`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username, email })
        });
        if (!res.ok) {
            console.error("create user failed:", res.status);
            return;
        }
        const u = await res.json();
        users = [...users, u];       // update list
        username = ""; email = "";   // clear inputs
    }

    async function loadUsers() {
        const res = await fetch(`${API_BASE}/users`);
        users = res.ok ? await res.json() : [];
    }

    // polls
    let creatorId = "";
    let question = "";
    let polls = [];
    let option1 = "Yes";
    let option2 = "No";

    async function createPoll() {
        if (!creatorId || !question) return;
        // include two options automatically
        const body = {
            question,
            validUntil: "2025-09-20T00:00:00Z",
            options: [
                { text: option1 },
                { text: option2 }
            ]
        };
        const res = await fetch(`${API_BASE}/polls?userId=${encodeURIComponent(creatorId)}`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(body)
        });
        if (!res.ok) return console.error("create poll failed:", await res.text());
        const p = await res.json();
        polls = [...polls, p];
        creatorId = ""; question = ""; option1 = "Yes"; option2 = "No";
    }

    async function loadPolls() {
        const res = await fetch(`${API_BASE}/polls`);
        polls = res.ok ? await res.json() : [];
    }

    // votes
    let voteUserId = "";
    let votePollId = "";
    let voteOption = "";

    async function castVote() {
        if (!voteUserId || !votePollId || !voteOption) return;
        const res = await fetch(
            `${API_BASE}/votes?userId=${encodeURIComponent(voteUserId)}&pollId=${encodeURIComponent(votePollId)}&option=${encodeURIComponent(voteOption)}`,
            { method: "POST" }
        );
        if (!res.ok) {
            console.error("vote failed:", res.status);
            return;
        }
        voteUserId = ""; votePollId = ""; voteOption = "";
        await loadPolls(); // automaticallly refresh
    }

    // initload
    loadUsers();
    loadPolls();
</script>

<div class="wrap">
    <!-- Users -->
    <section>
        <form on:submit|preventDefault={createUser}>
            <h3>Create User</h3>
            <input placeholder="Username" bind:value={username} />
            <input type="email" placeholder="Email" bind:value={email} />
            <button>Create user</button>
        </form>

        <h3>Users</h3>
        {#if users.length}
            <ul>
                {#each users as u}
                    <li>{u.id}: {u.username} ({u.email})</li>
                {/each}
            </ul>
        {:else}
            <p>No users yet.</p>
        {/if}
        <button on:click={loadUsers}>Refresh users</button>
    </section>

    <!-- create poll -->
    <section>
        <form on:submit|preventDefault={createPoll}>
            <h3>Create Poll</h3>
            <input type="number" placeholder="Creator userID" bind:value={creatorId} />
            <input placeholder="Question" bind:value={question} />
            <!-- NEW: two option inputs -->
            <input placeholder="Option 1" bind:value={option1} />
            <input placeholder="Option 2" bind:value={option2} />
            <button>Create poll</button>
        </form>

        <h3>Polls</h3>
        {#if polls.length}
            <ul>
                {#each polls as p}
                    <li>
                        <div><strong>#{p.id}</strong> {p.question}</div>
                        {#if p.options && p.options.length}
                            <div>Options:
                                <ul>
                                    {#each p.options as o}
                                        <li>{o.text}</li>
                                    {/each}
                                </ul>
                            </div>
                        {/if}
                        {#if p.votes}
                            <div>Votes: {p.votes.length}</div>
                        {/if}
                    </li>
                {/each}
            </ul>
        {:else}
            <p>No polls yet.</p>
        {/if}
        <button on:click={loadPolls}>Refresh polls</button>
    </section>
    <section>
        <form on:submit|preventDefault={castVote} style="margin-top:1rem">
            <h3>Vote on a Poll</h3>
            <input type="number" placeholder="User ID" bind:value={voteUserId} />
            <input type="number" placeholder="Poll ID" bind:value={votePollId} />
            <input placeholder="Option text (e.g. Yes)" bind:value={voteOption} />
            <button>Vote</button>
        </form>
    </section>
</div>
<style>
    .wrap {
        max-width: 1000px;
        margin: 15px;
        display: grid;
        grid-template-columns: 1fr 2fr 2fr; /* three columns */
        gap: 5rem;
        align-items: start;
    }
    h3 { margin: 1rem 0 .5rem; }
    form { display: grid; gap: .5rem; max-width: 320px; margin-bottom: 1rem; }
    input, button { padding: .5rem; }
    ul { padding-left: 1rem; }
</style>
