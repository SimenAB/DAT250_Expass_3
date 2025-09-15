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

    async function createPoll() {
        if (!creatorId || !question) return;
        const res = await fetch(`${API_BASE}/polls?userId=${encodeURIComponent(creatorId)}`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ question, validUntil: "2025-09-20T00:00:00Z" })
        });
        if (!res.ok) {
            console.error("create poll failed:", res.status);
            return;
        }
        const p = await res.json();
        polls = [...polls, p];
        creatorId = ""; question = "";
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
        await loadPolls(); // refresh after voting
    }

    // initial load
    loadUsers();
    loadPolls();
</script>


<!--form to create user-->
<form on:submit|preventDefault={createUser}>
    <h3>Create User</h3>
    <input placeholder="Username" bind:value={username} />
    <input type="email" placeholder="Email" bind:value={email} />
    <button>Create user</button>
</form>


<section>
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

<!-- form to create poll -->
<form on:submit|preventDefault={createPoll}>
    <h3>Create Poll</h3>
    <input type="number" placeholder="Creator userID" bind:value={creatorId} />
    <input placeholder="Question" bind:value={question} />
    <button>Create poll</button>
</form>

<!-- Polls list -->
<section>
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

<!-- Vote -->
<form on:submit|preventDefault={castVote}>
    <h2>Vote on a Poll</h2>
    <input type="number" placeholder="User ID" bind:value={voteUserId} />
    <input type="number" placeholder="Poll ID" bind:value={votePollId} />
    <input placeholder="Option text (e.g. Yes)" bind:value={voteOption} />
    <button>Vote</button>
</form>

<style>
    h3 { margin: 1rem 0 .5rem; }
    form { display: grid; gap: .5rem; max-width: 320px; margin-bottom: 1rem; }
    input, button { padding: .5rem; }
    ul { padding-left: 1rem; }
</style>
