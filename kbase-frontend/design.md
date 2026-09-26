# Gemini Notebook Design System Guidelines

## 1. Context and goals

**Design intent:** Gemini Notebook documentation UI must provide a
clean, functional, and implementation-ready experience that helps
developers quickly understand, navigate, and apply product knowledge.

Gemini Notebook is designed for developers and technical teams. The
interface must prioritize: - Clear information hierarchy. - Consistent
component behavior. - Accessible interaction patterns. - Scalable
documentation layouts.

The design system must use shared tokens and reusable components instead
of local visual exceptions.

------------------------------------------------------------------------

# 2. Design tokens and foundations

## Typography

The system must use:

``` css
font-family.primary: Google Sans Text
font-family.stack: Google Sans Text, Helvetica, Arial, sans-serif
font.size.base: 18px
font.weight.base: 400
font.lineHeight.base: 28px
```

Typography scale:

  Token           Value
  --------------- -------
  font.size.xs    15px
  font.size.sm    16px
  font.size.md    18px
  font.size.lg    24px
  font.size.xl    30px
  font.size.2xl   32px
  font.size.3xl   40px
  font.size.4xl   76px

Typography rules: - Headings must use the defined scale. - Body content
must use `font.size.base`. - Components must not introduce custom font
sizes.

------------------------------------------------------------------------

## Color tokens

The system must use semantic color tokens.

  Token                  Usage
  ---------------------- ------------------------
  color.surface.base     Main dark surface
  color.surface.muted    Light content surfaces
  color.surface.raised   Elevated containers
  color.text.secondary   Accent text
  color.text.tertiary    Link text
  color.text.inverse     Text on dark surfaces
  color.border.muted     Component borders

Raw color values must not be used directly inside components.

------------------------------------------------------------------------

## Spacing

The spacing system must use:

  Token     Value
  --------- --------
  space.1   8px
  space.2   12px
  space.3   13.5px
  space.4   16px
  space.5   20px
  space.6   24px
  space.7   25px
  space.8   26px

Components should use spacing tokens consistently.

------------------------------------------------------------------------

## Shape, shadow, and motion

  Token                     Value
  ------------------------- ----------------------------------
  radius.xs                 24px
  radius.sm                 83.2px
  radius.md                 94px
  shadow.1                  rgba(0,0,0,0.05) 0px 4px 8px 0px
  motion.duration.instant   300ms

Motion must support clear interaction feedback without causing
distraction.

------------------------------------------------------------------------

# 3. Component-level rules

## Header navigation

### Anatomy

-   Brand logo.
-   Primary navigation links.
-   Secondary actions.
-   Responsive menu trigger.

### States

Default: - Must use typography and spacing tokens.

Hover: - Must provide visible interaction feedback.

Focus-visible: - Must show keyboard focus indicator.

Active: - Must indicate current navigation location.

Disabled: - Must reduce interaction availability without removing
readability.

Loading: - Must display a non-blocking loading state.

Error: - Must communicate unavailable navigation actions.

### Responsive behavior

Desktop: - Navigation links remain visible.

Mobile: - Navigation must collapse into an accessible menu.

Keyboard: - Tab must move through navigation items logically. - Enter
and Space must activate controls.

Touch: - Interactive targets should provide adequate touch area.

------------------------------------------------------------------------

## Hero section

### Anatomy

-   Main headline.
-   Supporting description.
-   Primary action.
-   Product visual.

### Rules

-   Headline must use `font.size.4xl` on large screens.
-   Layout must maintain visual hierarchy.
-   Content must support long text wrapping.

States: - Default. - Hover action state. - Focus-visible action state. -
Active action state. - Disabled action state. - Loading action state. -
Error action state.

------------------------------------------------------------------------

## Buttons

### Variants

-   Primary.
-   Secondary.
-   Text action.

Buttons must have: - Clear labels. - Consistent spacing. - Visible focus
state.

Keyboard: - Enter and Space activate buttons.

Pointer: - Hover must provide feedback.

Touch: - Buttons must support touch interaction without precision
requirements.

------------------------------------------------------------------------

## Cards

Known page density: - 4 cards per page section.

Rules: - Cards must use consistent radius and shadow tokens. - Cards
must support: - Short content. - Long content. - Empty states. - Loading
states.

Overflow: - Text must wrap or truncate according to content
requirements.

------------------------------------------------------------------------

## Lists

Known page density: - 5 lists per page section.

Rules: - Lists must maintain readable spacing. - Long items must wrap
correctly. - Empty states must explain the missing content.

------------------------------------------------------------------------

## Links

Known page density: - 26 links per page.

Rules: - Links must have descriptive labels. - Links must use accessible
contrast. - Links must provide hover and focus-visible states.

------------------------------------------------------------------------

# 4. Accessibility requirements and testable acceptance criteria

Target standard: WCAG 2.2 AA.

## Keyboard accessibility

Must: - Allow all interactive elements to be reached by keyboard. -
Provide visible focus indicators. - Maintain logical focus order.

Acceptance test: - A user can complete all primary tasks without using a
pointer device.

------------------------------------------------------------------------

## Contrast

Must: - Maintain readable text contrast according to WCAG 2.2 AA.

Acceptance test: - Automated accessibility tools report no contrast
failures.

------------------------------------------------------------------------

## Focus-visible

Must: - Display a clear focus indicator.

Acceptance test: - Every interactive component shows visible focus
during keyboard navigation.

------------------------------------------------------------------------

## Content accessibility

Must: - Use meaningful headings. - Provide descriptive labels. - Avoid
ambiguous actions.

Acceptance test: - Screen readers can identify controls and page
structure.

------------------------------------------------------------------------

# 5. Content and tone standards

Writing style must be: - Concise. - Confident. - Implementation-focused.

Examples:

Recommended: \> Create a notebook and upload your source files.

Avoid: \> Click here to get started.

Recommended: \> Generate a video overview from your documentation.

Avoid: \> Amazing AI magic.

------------------------------------------------------------------------

# 6. Anti-patterns and prohibited implementations

The following implementations must not be used:

-   Custom spacing values outside the token system.
-   Low-contrast text.
-   Hidden focus indicators.
-   Ambiguous button labels.
-   Inconsistent component states.
-   Decorative elements that reduce usability.
-   One-off typography exceptions.

Teams should migrate existing components toward shared tokens and
documented states.

------------------------------------------------------------------------

# 7. QA checklist

## Visual

-   [ ] Components use approved tokens.
-   [ ] Typography follows the defined scale.
-   [ ] Spacing follows the spacing system.
-   [ ] No custom visual exceptions exist.

## Interaction

-   [ ] Hover states work.
-   [ ] Focus-visible states work.
-   [ ] Active states work.
-   [ ] Disabled states work.
-   [ ] Loading states work.
-   [ ] Error states work.

## Responsive

-   [ ] Desktop layouts work.
-   [ ] Mobile layouts work.
-   [ ] Long content does not break layouts.
-   [ ] Empty states are handled.

## Accessibility

-   [ ] Keyboard navigation works.
-   [ ] Focus indicators are visible.
-   [ ] Contrast passes WCAG 2.2 AA.
-   [ ] Screen reader labels are meaningful.
