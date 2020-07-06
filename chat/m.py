import markovify

with open("./filtered.txt", encoding="utf-8") as file:
    text = file.read();
    
model = markovify.NewlineText(text, state_size=2);

for i in range(5):
    print(model.make_sentence(max_overlap_ratio=0.7, tries=20))
    
print(len([key for key in model.chain.model.keys() if "___BEGIN__" in key]))